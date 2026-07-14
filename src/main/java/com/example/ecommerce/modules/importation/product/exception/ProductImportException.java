package com.example.ecommerce.modules.importation.product.exception;

public class ProductImportException extends RuntimeException {
    private final long line;

    public ProductImportException(long line, String message) {
        super("Erro na linha " + line + ": " + message);
        this.line = line;
    }

    public ProductImportException(long line, String message, Throwable cause) {
        super("Erro na linha " + line + ": " + message, cause);
        this.line = line;
    }

    public long getLine() {
        return line;
    }
}
