package com.example.demo.service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payloads.PostDto;
import com.example.demo.payloads.PostResponse;
import com.example.demo.repository.CategoryRepo;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.UserRepo;

@Service
public class PostServiceImpl implements PostService{
	
	
	//private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);


	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;


	
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryid) {
		
		User user=userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		Category category=categoryRepo.findById(categoryid).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryid));
		
		Post post = modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
	
		Post newPost=postRepo.save(post);
		return modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
		post.setTitle(post.getTitle());
		post.setContent(post.getContent());
		post.setImageName(post.getImageName());
		
		Post posts=postRepo.save(post);
		return modelMapper.map(posts, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		Post post=postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
		
		postRepo.delete(post);
		
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortdir) {
		Sort sort=null;
		if(sortdir.equals("asc")){
			sort = Sort.by(sortBy).ascending();	
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		
		 Pageable p = PageRequest.of(pageNumber, pageSize,sort);
	  Page<Post> pagePost = postRepo.findAll(p);
	  List<Post> posts=pagePost.getContent();
		List<PostDto> allPosts= posts.stream()
			    .map((post) -> modelMapper.map(post, PostDto.class))
			    .collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(allPosts);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPage(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostbyId(Integer postId) {
		Post findPostId = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
		return modelMapper.map(findPostId, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {

		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		
		List<Post> posts = postRepo.findByCategory(category);
		 
		List<PostDto> postDtos = posts.stream()
			    .map((post) -> modelMapper.map(post, PostDto.class))
			    .collect(Collectors.toList());
		
		

		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		
		List<Post> posts = postRepo.findByUser(user);
		
	    
	    List<PostDto> postDtos = posts.stream()
	            .map(post -> modelMapper.map(post, PostDto.class))
	            .collect(Collectors.toList());
	    
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts = postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream()
	            .map(post -> modelMapper.map(post, PostDto.class))
	            .collect(Collectors.toList());
		return postDtos;
	}

}
