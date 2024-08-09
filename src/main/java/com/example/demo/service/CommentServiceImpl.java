package com.example.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payloads.CommentDto;
import com.example.demo.repository.CommentRepo;
import com.example.demo.repository.PostRepo;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComments(CommentDto commentDto, Integer postid) {
		Post post = postRepo.findById(postid).orElseThrow(() -> new ResourceNotFoundException("post", "id", postid));
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment saveComment = commentRepo.save(comment);
		return modelMapper.map(saveComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("commnet", "id", commentId));
		commentRepo.delete(comment);
	}

}
