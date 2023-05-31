import { Customer } from "./customer";

export interface Order {
    id: number;
    products: [];
    customer: Customer;
}