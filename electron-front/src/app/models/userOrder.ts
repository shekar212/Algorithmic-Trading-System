export interface UserOrder {
    meta: Meta;
    orders:Order[];
}

interface Meta {
    cycles: number;
    stocks:number;
    interval:string;
    targetPercent:number;
    stopLoss:number;
}

interface Order {
    id :number;
    price: number;
    time : string;
    type: string;

}