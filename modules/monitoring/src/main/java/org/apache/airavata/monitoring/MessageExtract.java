package org.apache.airavata.monitoring;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Represents the required data extracted from javax.mail.Message, which is serializable
 *
 * @author Siddharth Jain
 */
public class MessageExtract implements Serializable {
    private static final long serialVersionUID = 8129498758236043568L;
    private Address from;
    private Address[] recipients;
    private String content;
    private String subject;

    public MessageExtract(Message message) throws MessagingException {
        String subject = message.getSubject();
        // TODO fetch the actual content
        String content = "content";
        Address from = message.getFrom()[0];
        Address[] recepientAddresses = message
                .getRecipients(Message.RecipientType.TO);
        this.from = from;
        this.recipients = recepientAddresses;
        this.content = content;
        this.subject = subject;
    }

    public MessageExtract(Address from, Address[] recipients, String content,
                          String subject) {
        super();
        this.from = from;
        this.recipients = recipients;
        this.content = content;
        this.subject = subject;
    }

    public Address getFrom() {
        return from;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public Address[] getRecipients() {
        return recipients;
    }

    public void setRecipients(Address[] recipients) {
        this.recipients = recipients;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private void writeObject(ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }

    /**
     * Get Serialized bytes of the instance
     *
     * @return Serialized bytes of the instance
     * @throws IOException
     */
    public byte[] getSerializedBytes() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(this);
        byte[] msgExtractBytes = bos.toByteArray();
        return msgExtractBytes;
    }

    @Override
    public String toString() {
        return "EmailMessage [from=" + from + ", recipients="
                + Arrays.toString(recipients) + ", content=" + content
                + ", subject=" + subject + "]";
    }

}
