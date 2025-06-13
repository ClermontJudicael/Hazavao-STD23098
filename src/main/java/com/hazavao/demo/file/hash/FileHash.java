package com.hazavao.demo.file.hash;

import com.hazavao.demo.PojaGenerated;

@PojaGenerated
public record FileHash(FileHashAlgorithm algorithm, String value) {}
