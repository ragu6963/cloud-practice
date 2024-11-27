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
    file: null,
  });

  async function fetchPosts() {
    const response = await postsApi.getPosts();
    setPosts(response.reverse());
  }

  const handleFormChange = (e) => {
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
      file: file,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const uploadData = new FormData();
    uploadData.append("title", formData.title);
    uploadData.append("content", formData.content);
    if (formData.file) uploadData.append("file", formData.file);

    await postsApi.postPost(uploadData);

    setFormData({ title: "", content: "", file: null });
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
        handleFormChange={handleFormChange}
        handleFileChange={handleFileChange}
        formData={formData}
      ></PostForm>
      {posts.map((post) => {
        return <Post key={post.id} id={post.id} title={post.title}></Post>;
      })}
    </div>
  );
}
