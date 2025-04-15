export interface Post{
    id:number;
    text:string;
    createdAt:Date;
    author:string;
    reply:Post;
    pageNum:number;
    likedByUser:boolean,
    likes:number
}