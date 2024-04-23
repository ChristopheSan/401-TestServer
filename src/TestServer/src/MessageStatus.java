public enum MessageStatus {
    // Status for chat messages
    SENT,       // Message is sent
    DELIVERED,  // Message is sent and succesfully delivered to recipient/s
    // FAILED,     // Failed message
    READ,       // Message is read/opened by the recipient/s

    // Technical Status
    SUCCESS,
    FAILED,
    PENDING,

    // Default status
    UNDEFINED
}
