package io.github.abreufelipedev.dto;

import java.util.Date;

public record CommentDTO(String text, Date date, AuthorDTO author) {
}
