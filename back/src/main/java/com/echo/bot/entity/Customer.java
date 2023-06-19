package com.echo.bot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "customer")
@NoArgsConstructor
public class Customer {

    public Customer(long chatId, long userId, String lastMessage, long index, Delay delay) {
        this.chatId = chatId;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.index = index;
        this.delay = delay;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "chat_id")
    private long chatId;

    @Column(nullable = false, name = "user_id")
    private long userId;

    @Column(nullable = false, name = "last_msg")
    private String lastMessage;

    @Column(nullable = false)
    private long index;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delay_id")
    private Delay delay;
}
