package com.example.aderbal.bookshareapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Friend {
    public String friendEmail;
    public String friendName;
    public String status = "pending";
    //public String friendKey;

    public Friend() {

    }

    public Friend(String friendEmail, String friendName) {
        this.friendEmail = friendEmail;
        this.friendName = friendName;
        //TODO: Adicionar key do amigo, para fazer referência ao amigo e então pegar os livros do mesmo.
        //A referência é feita por e-mail, que é único, não se é salvo em key.
    }

    /*public Friend(String friendEmail, String friendName, String friendKey) {
        this.friendEmail = friendEmail;
        this.friendName = friendName;
        this.friendKey = friendKey;
        //TODO: Adicionar key do amigo, para fazer referência ao amigo e então pegar os livros do mesmo.
    }*/
}
