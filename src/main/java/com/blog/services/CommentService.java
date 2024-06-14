package com.blog.services;

import com.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto comment , Integer postId);
	
	void deleteComment(Integer commentId);
}
