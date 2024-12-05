package entity;

import java.time.LocalDateTime;

/**
 * Represents a chat message with role, content, and timestamp.
 */
public class ChatMessage {
    private String role;
    private String content;
    private LocalDateTime timestamp;

    /**
     * Constructs a ChatMessage object.
     *
     * @param role    The role of the message sender ("system", "user", "assistant").
     * @param content The content of the message.
     */
    public ChatMessage(String role, String content) {
        this.role = role;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructor that accepts role, content, and timestamp.
     *
     * @param role      The role of the message sender ("system", "user", "assistant").
     * @param content   The content of the message.
     * @param timestamp The timestamp of the message.
     */
    public ChatMessage(String role, String content, LocalDateTime timestamp) {
        this.role = role;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters
    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
