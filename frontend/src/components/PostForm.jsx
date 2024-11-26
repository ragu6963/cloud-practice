import React from "react";
import styles from "./PostForm.module.css";

export default function PostForm({
  handleSubmit,
  handleChange,
  handleFileChange,
  formData,
}) {
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
            onChange={handleChange}
            placeholder="title 입력"
            className={styles.input}
          />
        </div>
        <div>
          <textarea
            id="content"
            name="content"
            value={formData.content}
            onChange={handleChange}
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
