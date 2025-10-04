package com.example.WebApplicationDesign.models;

import lombok.Getter;

public class Projections
{
    @Getter
    public static class UsersDTO
    {
        private final int id;
        private final String name;
        private final String phoneNumber;
        private final String email;
        public UsersDTO(int id, String name, String phoneNumber, String email){
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.id = id;
        }
    }
}
