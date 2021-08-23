package iva.lesperance.api.tutorial.email;

import java.util.concurrent.CompletableFuture;

public interface EmailSender {
    CompletableFuture<Boolean> send(String to, String email);
}
