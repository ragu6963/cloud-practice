import { useRef, useState } from "react";
import axios from "axios";

export default function FileInput({}) {
  const baseURL = "http://localhost:8080/";
  const [formData, setFormData] = useState({ file: null });
  const fileInputRef = useRef(null);

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setFormData((prev) => ({ ...prev, file }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();

    if (formData.file) {
      formData.append("file", formData.file);
    }

    await axios.post(`${baseURL}api/posts`, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  };

  return (
    <form onSubmit={handleSubmit}>
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
  );
}
