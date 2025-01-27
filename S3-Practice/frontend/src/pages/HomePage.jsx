import { useEffect, useState } from "react";
import postsApi from "../api/postsApi";
import PostForm from "../components/PostForm";
import Post from "../components/Post";

export default function HomePage() {
  const [posts, setPosts] = useState([]);

  async function fetchPosts() {
    const response = await postsApi.getPosts();
    setPosts(response.reverse());
  }

  useEffect(() => {
    fetchPosts();
  }, []);

  return (
    <div>
      <PostForm fetchPosts={fetchPosts}></PostForm>
      {posts.map((post) => {
        return <Post key={post.id} post={post} isDetail={false}></Post>;
      })}
    </div>
  );
}
