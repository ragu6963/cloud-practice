import React from "react";
import styles from "./PostForm.module.css";
import { useRef, useState } from "react";
import postsApi from "../api/postsApi";

const INITIAL_FORM_DATA = {
  title: "",
  content: "",
  file: null,
};

export default function PostForm({ fetchPosts }) {
  const [inputData, setInputData] = useState(INITIAL_FORM_DATA);
  const fileInputRef = useRef(null);

  const handleFormChange = (e) => {
    const { name, value } = e.target;
    setInputData((prev) => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setInputData((prev) => ({ ...prev, file }));
  };

  const resetForm = () => {
    setInputData(INITIAL_FORM_DATA);
    if (fileInputRef.current) fileInputRef.current.value = null;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const uploadData = new FormData();
    uploadData.append("title", inputData.title);
    uploadData.append("content", inputData.content);
    if (inputData.file) uploadData.append("file", inputData.file);

    try {
      await postsApi.postPost(uploadData);
      fetchPosts();
      resetForm();
    } catch (error) {
      console.error("ERROR : ", error);
    }
  };

  return (
    <div className={styles.postFormContainer}>
      <form onSubmit={handleSubmit} className={styles.form}>
        <input
          type="text"
          id="title"
          name="title"
          value={inputData.title}
          onChange={handleFormChange}
          placeholder="title 입력"
          className={styles.input}
        />
        <textarea
          id="content"
          name="content"
          value={inputData.content}
          onChange={handleFormChange}
          placeholder="content 입력"
          className={styles.textarea}
        />
        <input
          type="file"
          name="file"
          id="file"
          ref={fileInputRef}
          onChange={handleFileChange}
          accept="image/*"
        />
        <button type="submit" className={styles.button}>
          Post 생성
        </button>
      </form>
    </div>
  );
}
