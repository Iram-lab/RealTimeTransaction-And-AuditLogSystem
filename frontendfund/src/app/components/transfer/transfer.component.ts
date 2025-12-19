import { Component } from '@angular/core';
import { TransactionService } from '../../services/transaction.service';
import { TransferPayload } from '../../interface/transfer-payload';


@Component({
  selector: 'app-transfer',
  standalone:false,
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent {
  receiverId!: number;
  amount!: number;

  constructor(private transactionService: TransactionService) {}

  sendMoney() {
    const senderId = parseInt(localStorage.getItem('userId') || '0');
    const payload: TransferPayload = { senderId, receiverId: this.receiverId, amount: this.amount };
    this.transactionService.transfer(payload).subscribe({
      next: () => alert('Transfer successful'),
      error: (err) => alert('Transfer failed')
    });
  }
}
