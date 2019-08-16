import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Mannager } from './mannager.model';
import { MannagerService } from './mannager.service';

@Component({
    selector: 'jhi-mannager-detail',
    templateUrl: './mannager-detail.component.html'
})
export class MannagerDetailComponent implements OnInit, OnDestroy {

    mannager: Mannager;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mannagerService: MannagerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMannagers();
    }

    load(id) {
        this.mannagerService.find(id).subscribe((mannager) => {
            this.mannager = mannager;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMannagers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mannagerListModification',
            (response) => this.load(this.mannager.id)
        );
    }
}
