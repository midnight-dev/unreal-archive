package net.shrimpworks.unreal.archive.content;

import java.nio.file.Path;
import java.time.LocalDateTime;

public interface ContentEntity<T extends ContentEntity<T>> extends Comparable<T> {

	public Path contentPath(Path root);

	public Path slugPath(Path root);

	public Path pagePath(Path root);

	public String game();

	public String name();

	public String author();

	public String releaseDate();

	public String autoDescription();

	public LocalDateTime addedDate();

	public String contentType();

	public String friendlyType();

	public String leadImage();
}
