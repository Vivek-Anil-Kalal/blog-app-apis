package com.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.entities.Comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;
	@NotBlank
	@Size(min = 4 , message = "Title should be minimum of 4 char")
	private String title;
	@NotBlank
	@Size(min = 10 , message = "Content should be minimum of 10 char")
	private String content;

	private String imageName;
	private Date addedDate;
	private CategoryDto category;
	private UserDto user;

	private Set<CommentDto> comments = new HashSet<>();
}
