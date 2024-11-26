import { useEffect, useState } from "react";
import postsApi from "../api/postsApi";
import PostForm from "../components/PostForm";
import Post from "../components/Post";

export default function HomePage() {
  const [message, setMessage] = useState("");
  const [posts, setPosts] = useState([]);

  const [formData, setFormData] = useState({
    title: "",
    content: "",
  });

  async function fetchPosts() {
    const response = await postsApi.getPosts();
    setPosts(response.reverse());
  }

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setFormData((prevData) => ({
      ...prevData,
      imageUrl: file,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await postsApi.postPost(formData);
    setFormData({ title: "", content: "" });
    fetchPosts();
  };

  useEffect(() => {
    async function fetchMessage() {
      const response = await postsApi.getMessage();
      setMessage(response.message);
    }
    fetchMessage();
    fetchPosts();
  }, []);

  return (
    <div>
      <h1>{message}</h1>
      <PostForm
        handleSubmit={handleSubmit}
        handleChange={handleChange}
        handleFileChange={handleFileChange}
        formData={formData}
      ></PostForm>
      {posts.map((post) => {
        return <Post key={post.id} id={post.id} title={post.title}></Post>;
      })}
    </div>
  );
}
