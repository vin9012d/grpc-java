package com.protobind;

import com.vinod.demo.protobind.proto.PersonProto;
public class Person {
    public static void main(String[] args) {
        // Build a Person message
        PersonProto.Person person = PersonProto.Person.newBuilder()
                .setFirstName("Vinodbhai Chaudhari")
                .build();

        // Print the message
        System.out.println("Protobuf Message:");
        System.out.println(person);
        
        // Print just the name
        System.out.println("\nName field: " + person.getFirstName());
        
        // Print as JSON-like format
        System.out.println("\nFormatted output:");
        System.out.println("Person { name: \"" + person.getFirstName() + "\" }");
    }
}
