import { useEffect, useState } from "react";
import postsApi from "../api/postsApi";
import PostForm from "../components/PostForm";
import Post from "../components/Post";

export default function HomePage() {
  const [message, setMessage] = useState("");
  const [posts, setPosts] = useState([]);

  async function fetchPosts() {
    const response = await postsApi.getPosts();
    setPosts(response.reverse());
  }

  async function fetchMessage() {
    const response = await postsApi.getMessage();
    setMessage(response.message);
  }
  useEffect(() => {
    fetchMessage();
    fetchPosts();
  }, []);

  return (
    <div>
      <h1>{message}</h1>
      <PostForm fetchPosts={fetchPosts}></PostForm>
      {posts.map((post) => {
        return <Post key={post.id} post={post} isDetail={false}></Post>;
      })}
    </div>
  );
}
