import { Post } from "./post";

export interface PostsByPage{
    content:Post[];
    totalPages:number;
}