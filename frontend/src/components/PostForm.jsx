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
  const [formData, setFormData] = useState(INITIAL_FORM_DATA);
  const fileInputRef = useRef(null);

  const handleFormChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setFormData((prev) => ({ ...prev, file }));
  };

  const resetForm = () => {
    setFormData(INITIAL_FORM_DATA);
    if (fileInputRef.current) fileInputRef.current.value = null;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const uploadData = new FormData();
    uploadData.append("title", formData.title);
    uploadData.append("content", formData.content);
    if (formData.file) uploadData.append("file", formData.file);

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
      <form
        onSubmit={handleSubmit}
        className={styles.form}
        encType="multipart/form-data"
      >
        <div>
          <input
            type="text"
            id="title"
            name="title"
            value={formData.title}
            onChange={handleFormChange}
            placeholder="title 입력"
            className={styles.input}
          />
        </div>
        <div>
          <textarea
            id="content"
            name="content"
            value={formData.content}
            onChange={handleFormChange}
            placeholder="content 입력"
            className={styles.textarea}
          />
        </div>
        <div>
          <div>
            <input
              type="file"
              name="file"
              id="file"
              ref={fileInputRef}
              onChange={handleFileChange}
              accept="image/*"
            />
          </div>
        </div>
        <button type="submit" className={styles.button}>
          Post 생성
        </button>
      </form>
    </div>
  );
}
