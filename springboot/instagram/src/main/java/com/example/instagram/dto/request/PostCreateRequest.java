package com.example.instagram.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class PostCreateRequest {
    @NotBlank
    @Length(min = 1, max = 1000, message = "내용은 1000자 이내로 작성해 주세요.")
    private String content;
}
