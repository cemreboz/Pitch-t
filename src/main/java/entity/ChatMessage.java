package entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Represents a chat message with role, content, and timestamp.
 */
public class ChatMessage {
    private String role; // "system", "user", or "assistant"
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatMessage)) return false;
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(role, that.role) &&
                Objects.equals(content, that.content) &&
                Objects.equals(truncateTimestamp(timestamp), truncateTimestamp(that.timestamp));
    }

    private LocalDateTime truncateTimestamp(LocalDateTime timestamp) {
        return timestamp != null ? timestamp.truncatedTo(ChronoUnit.SECONDS) : null;
    }

    // Override hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(role, content, timestamp);
    }
}
