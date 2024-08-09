package com.example.demo.service;

import com.example.demo.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComments(CommentDto commentDto,Integer postid);
	
	void deleteComment(Integer commentId);
	

}
