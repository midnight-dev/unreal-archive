package net.shrimpworks.unreal.archive.scraper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import net.shrimpworks.unreal.archive.CLI;
import net.shrimpworks.unreal.archive.YAML;
import net.shrimpworks.unreal.archive.indexer.Submission;

public class Downloader {

	static final String USER_AGENT = "unreal-archiver/1.0";

	private static final Pattern AUTOINDEXPHP_PATTERN = Pattern.compile("(.+)/index.php\\?dir=([^&]+)&file=(.+)");
	private static final String AUTOINDEXPHP_REWRITE = "%s/%s%s"; // host/dur/file

	private static final Pattern CONTENT_DISPOSITION_FILENAME = Pattern.compile(".+filename=\"?([^\"]+)\"?");
	//attachment; filename="DM-Morbias_Arena.zip";

	public static void download(CLI cli) throws IOException {
		if (cli.commands().length < 2) throw new IllegalArgumentException("An input file list is required!");
		if (cli.commands().length < 3) throw new IllegalArgumentException("An output directory is required!");

		final Path fileListPath = Paths.get(cli.commands()[1]);
		if (!Files.exists(fileListPath)) throw new IllegalArgumentException("Input file list does not exist: " + fileListPath.toString());

		final Path output = Paths.get(cli.commands()[2]);
		if (!Files.isDirectory(output)) throw new IllegalArgumentException("Output path is not a directory: " + output.toString());

		final long slowdown = Long.valueOf(cli.option("slowdown", "2500"));

		List<Found.FoundUrl> urls = YAML.fromFile(fileListPath, new TypeReference<List<Found.FoundUrl>>() {});

		for (int i = 0; i < urls.size(); i++) {
			Found.FoundUrl url = urls.get(i);

			Path dir = output.resolve(url.path);
			if (!Files.isDirectory(dir)) Files.createDirectories(dir);

			Path ymlFile = dir.resolve(url.name + ".yml");
			Path outFile = dir.resolve(url.name);

			if (!Files.exists(ymlFile)) {

				String dl = url.url;
				Matcher m = AUTOINDEXPHP_PATTERN.matcher(dl);
				if (m.matches()) {
					dl = String.format(AUTOINDEXPHP_REWRITE, m.group(1), m.group(2), m.group(3));
				}

				System.out.println("Downloading from " + dl);

				Response response = Request.Get(url.url).userAgent(USER_AGENT).execute();
				HttpResponse httpResponse = response.returnResponse();
				if (httpResponse.getStatusLine().getStatusCode() >= 400) {
					System.out.println(httpResponse.getStatusLine().getReasonPhrase());
					continue;
				}

				Header disposition = httpResponse.getFirstHeader("Content-Disposition");
				if (disposition != null) {
					Matcher matcher = CONTENT_DISPOSITION_FILENAME.matcher(disposition.getValue());
					if (matcher.matches()) {
						outFile = dir.resolve(matcher.group(1));
						ymlFile = dir.resolve(matcher.group(1) + ".yml");
					}
				}

				Files.copy(httpResponse.getEntity().getContent(), outFile, StandardCopyOption.REPLACE_EXISTING);

				Submission sub = new Submission(outFile, url.pageUrl);
				Files.write(ymlFile, YAML.toString(sub).getBytes(StandardCharsets.UTF_8),
							StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

				System.out.println("Wrote file to " + outFile.toString());

				try {
					if (slowdown > 0) {
						long deadline = System.currentTimeMillis() + slowdown;
						while (System.currentTimeMillis() < deadline) {
							Thread.sleep(250);
							System.out.printf("Completed %d of %d; Waiting %dms\r", i + 1, urls.size(),
											  deadline - System.currentTimeMillis());
						}
					}
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

			System.out.printf("Completed %d of %d\r", i + 1, urls.size());
		}
	}
}
