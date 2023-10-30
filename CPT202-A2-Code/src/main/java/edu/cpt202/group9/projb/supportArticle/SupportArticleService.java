package edu.cpt202.group9.projb.supportArticle;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface SupportArticleService {
    
    public void init();

    public void save(MultipartFile file);

    public Resource load(String filename);

    public Stream<Path> loadAll();

}
