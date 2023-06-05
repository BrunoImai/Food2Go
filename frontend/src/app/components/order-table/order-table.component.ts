import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {animate, state, style, transition, trigger} from '@angular/animations';
import { OrderService } from 'src/app/services/order.service';
import { Order } from 'src/app/models/order';
import { Customer } from 'src/app/models/customer';

@Component({
  selector: 'app-order-table',
  templateUrl: './order-table.component.html',
  styleUrls: ['./order-table.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})


export class OrderTableComponent implements AfterViewInit {
  nameColumns: string[] = ['id', 'name', 'costumer', 'products'];
  displayColumns: string[] = ['ID', 'Status', 'Nome do cliente', 'Produtos']
  columnsToDisplayWithExpand = [...this.nameColumns, 'expand'];
  public dataSource: any = [];
  expandedOrder!: Order | null;
  order!: Order;
  customer!: Customer;
  

  constructor(private orderService: OrderService) {}

  @ViewChild(MatSort)
  sort: MatSort = new MatSort;

  ngAfterViewInit() {
    this.orderService.getOrders().subscribe((data: any)=>{
      this.dataSource = data;
    });
  }
}
