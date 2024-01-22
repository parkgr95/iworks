package com.example.iworks.model.entity;

import lombok.EqualsAndHashCode;

import java.awt.*;
import java.io.Serializable;

@EqualsAndHashCode
public class UserMeetingId implements Serializable {
    private int meeting;
    private int user;
}
