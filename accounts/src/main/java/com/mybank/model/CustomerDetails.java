package com.mybank.model;

import com.mybank.accounts.Account;

import java.util.List;

public record CustomerDetails (
        Account account,
        List<Card> cards)
{}
