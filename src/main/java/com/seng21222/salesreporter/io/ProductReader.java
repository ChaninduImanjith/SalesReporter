package com.seng21222.salesreporter.io;

import com.seng21222.salesreporter.exception.InvalidDataException;
import com.seng21222.salesreporter.model.Product;
import java.io.IOException;
import java.util.List;

public interface ProductReader {
    List<Product> readProducts(String filePath) throws IOException, InvalidDataException;
}