package com.foolday.service.api.base;

import java.io.File;
import java.util.Optional;

public interface ImageServiceApi {
    Optional<String> upload(File file);
}
