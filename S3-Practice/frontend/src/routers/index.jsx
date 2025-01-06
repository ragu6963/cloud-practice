import { createBrowserRouter } from "react-router-dom";
import HomePage from "../pages/HomePage";
import PostPage from "../pages/PostPage";

const router = createBrowserRouter([
  {
    path: "",
    element: <HomePage />,
  },
  {
    path: "/post/:postId",
    element: <PostPage />,
  },
]);

export default router;
