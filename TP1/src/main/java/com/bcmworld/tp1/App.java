package com.bcmworld.tp1;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello Rochi");
        get("/saclier", (req, res) -> "Lucas");
    }
}
