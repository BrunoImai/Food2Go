import {LiveAnnouncer} from '@angular/cdk/a11y';
import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatSort, Sort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { OrderService } from 'src/app/services/order.service';
import { Order } from 'src/app/interfaces/order';
import { Customer } from 'src/app/interfaces/customer';

@Component({
  selector: 'app-order-table',
  templateUrl: './order-table.component.html',
  styleUrls: ['./order-table.component.css']
})

export class OrderTableComponent implements AfterViewInit {
  displayColumns: string[] = ['id', 'nome', 'telefone', 'produtos'];
  public dataSource: any = [];
  order!: Order;
  customer!: Customer;
  

  constructor(private _liveAnnouncer: LiveAnnouncer, private orderService: OrderService) {}

  @ViewChild(MatSort)
  sort: MatSort = new MatSort;

  ngAfterViewInit() {
    this.orderService.getOrders().subscribe((data: any)=>{
      this.dataSource = data;
    });
    this.dataSource.sort = this.sort;
  }
  

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Final ${sortState.direction}Classificado`);
    } else {
      this._liveAnnouncer.announce('Removido organização');
    }
  }
}
