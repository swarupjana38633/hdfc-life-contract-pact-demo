package com.hli.pact.consumer.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Product {

    private String id;
    private String type;
    private String name;
    private String version;
}
