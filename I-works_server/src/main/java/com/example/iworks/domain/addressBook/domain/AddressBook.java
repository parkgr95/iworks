package com.example.iworks.domain.addressBook.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address_book")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressBook {
    @Id @GeneratedValue
    @Column(name = "schedule_id")
    private Integer scheduleId; // 할 일 아이디
}
