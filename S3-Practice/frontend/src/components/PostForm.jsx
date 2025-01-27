import React from "react";
import styles from "./PostForm.module.css";
import { useRef, useState } from "react";
import postsApi from "../api/postsApi";

const INITIAL_FORM_DATA = {
  title: "",
  content: "",
  file: null, // 이미지 파일을 저장할 state
};

export default function PostForm({ fetchPosts }) {
  const [inputData, setInputData] = useState(INITIAL_FORM_DATA);
  const fileInputRef = useRef(null); // 파일 input 요소에 대한 참조

  const resetForm = () => {
    setInputData(INITIAL_FORM_DATA);
    if (fileInputRef.current) fileInputRef.current.value = null; // 파일 input 초기화
  };

  const handleFormChange = (e) => {
    const { name, value } = e.target;
    setInputData((prev) => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0]; // 선택된 첫 번째 파일 가져오기
    setInputData((prev) => ({ ...prev, file })); // 파일 state 업데이트
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData(); // 멀티파트 폼데이터 생성
    formData.append("title", inputData.title);
    formData.append("content", inputData.content);

    if (inputData.file) formData.append("file", inputData.file); // 이미지 파일이 있는 경우에 폼데이터에 추가

    try {
      await postsApi.postPost(formData);
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
          accept="image/*" // 이미지 파일만 선택 가능하도록 제한
        />
        <button type="submit" className={styles.button}>
          Post 생성
        </button>
      </form>
    </div>
  );
}
