import { Customer } from "./customer";

export interface Order {
    id: string;
    name: string;
    customer: string;
    products: any[]; 
    [key: string]: any;  
}