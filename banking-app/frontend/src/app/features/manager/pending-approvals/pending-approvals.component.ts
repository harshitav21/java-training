import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApprovalService } from '../../../core/services/approval.service';
import { Location } from '@angular/common';


@Component({
  selector: 'app-pending-approvals',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pending-approvals.component.html'
})
export class PendingApprovalsComponent implements OnInit {

  approvals: any[] = [];
  loading = false;

  constructor(
    private approvalService: ApprovalService,
    private location: Location) { }

  ngOnInit() {
    this.loadPending();
  }

  loadPending() {
    this.loading = true;

    this.approvalService.getPendingApprovals().subscribe({
      next: (data) => {
        this.approvals = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  approve(ref: string) {
    this.approvalService.approveOrReject(ref, true).subscribe(() => {
      this.loadPending();
    });
  }

  reject(ref: string) {
    this.approvalService.approveOrReject(ref, false).subscribe(() => {
      this.loadPending();
    });
  }

  goBack() {
  this.location.back();
}
  
}
