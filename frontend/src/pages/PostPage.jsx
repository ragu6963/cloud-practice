import React from "react";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import postsApi from "../api/postsApi";
import Post from "../components/Post";
export default function PostPage() {
  const { postId } = useParams();
  const [post, setPost] = useState({});

  useEffect(() => {
    async function fetchPost() {
      const response = await postsApi.getPost(postId);
      setPost(response);
    }
    fetchPost();
  }, []);

  return (
    <div>
      <Post id={post.id} title={post.title} content={post.content}></Post>
    </div>
  );
}
