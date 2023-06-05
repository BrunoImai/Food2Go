import { Component, DefaultIterableDiffer, OnInit } from '@angular/core'
import { MatDialog } from '@angular/material/dialog'
import { MatTableDataSource } from '@angular/material/table'
import { Product, ProductColumns } from 'src/app/models/product'
import { MenuService } from 'src/app/services/menu.service'


@Component({
  selector: 'app-menu-table',
  templateUrl: './menu-table.component.html',
  styleUrls: ['./menu-table.component.css']
})
export class MenuTableComponent {
  displayedColumns: string[] = ProductColumns.map((col) => col.key)
  columnsSchema: any = ProductColumns
  dataSource = new MatTableDataSource<Product>()
  valid: any = {}

  constructor(public dialog: MatDialog, private menuService: MenuService) {}

  ngOnInit() {
    this.menuService.getProducts().subscribe((res: any) => {
      this.dataSource.data = res
    })
  }

  editRow(row: Product) {
    if (row.id === 0) {
      this.menuService.addProduct(row).subscribe((newProduct: Product) => {
        row.id = newProduct.id
        row.isEdit = false
      })
    } else {
      // TODO: update product
      // this.menuService.updateProduct(row).subscribe(() => (row.isEdit = false))
    }
  }

  addRow() {
    const newRow: Product = {
      name: '',
      price: 0,
      qtt: 0,
      description: '',
    }
    this.dataSource.data = [newRow, ...this.dataSource.data]
  }

  // removeRow(id: number) {
  //   this.menuService.deleteProduct(id).subscribe(() => {
  //     this.dataSource.data = this.dataSource.data.filter(
  //       (u: Product) => u.id !== id,
  //     )
  //   })
  // }

  // removeSelectedRows() {
  //   const users = this.dataSource.data.filter((u: Product) => u.isSelected)
  //   this.dialog
  //     .subscribe((confirm) => {
  //       if (confirm) {
  //         this.menuService.deleteProducts(users).subscribe(() => {
  //           this.dataSource.data = this.dataSource.data.filter(
  //             (u: Product) => !u.isSelected,
  //           )
  //         })
  //       }
  //     })
  // }

  inputHandler(e: any, id: number, key: string) {
    if (!this.valid[id]) {
      this.valid[id] = {}
    }
    this.valid[id][key] = e.target.validity.valid
  }

  disableSubmit(id: number) {
    if (this.valid[id]) {
      return Object.values(this.valid[id]).some((item) => item === false)
    }
    return false
  }

}