import { Component, OnInit } from '@angular/core';
import { TransactionService } from '../../services/transaction.service';
import { TransactionHistory } from '../../interface/transaction-history';

@Component({
  selector: 'app-history',
  standalone: false,
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  transactions: TransactionHistory[] = [];
  userId = parseInt(localStorage.getItem('userId') || '0');

  sortColumn: keyof TransactionHistory | '' = '';
  sortAsc: boolean = true;

  constructor(private transactionService: TransactionService) {
    console.log('[HistoryComponent] Constructor called');
  }

  ngOnInit() {
    console.log('[HistoryComponent] ngOnInit | userId =', this.userId);

    this.transactionService.getHistory(this.userId).subscribe({
      next: (res) => {
        console.log('[HistoryComponent] Transaction history fetched:', res);
        this.transactions = res;
      },
      error: (err) => {
        console.error('[HistoryComponent] Error fetching transaction history', err);
      }
    });
  }

  sort(column: keyof TransactionHistory) {
    console.log(
      `[HistoryComponent] Sort clicked | column=${column}`
    );

    if (this.sortColumn === column) {
      this.sortAsc = !this.sortAsc;
      console.log('[HistoryComponent] Toggled sort order | asc=', this.sortAsc);
    } else {
      this.sortColumn = column;
      this.sortAsc = true;
      console.log('[HistoryComponent] Sorting by new column | asc=true');
    }

    this.transactions.sort((a: any, b: any) => {
      const valueA = a[column];
      const valueB = b[column];

      if (typeof valueA === 'number') {
        return this.sortAsc ? valueA - valueB : valueB - valueA;
      }

      return this.sortAsc
        ? valueA.toString().localeCompare(valueB.toString())
        : valueB.toString().localeCompare(valueA.toString());
    });

    console.log('[HistoryComponent] Sorted transactions:', this.transactions);
  }
}
