<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Age verification</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are You over 18 years old? If not, you will not be allowed to see details of alcoholic
                beverages.
            </div>
            <div class="modal-footer txt-center">

                <form action="/search" method="GET">

                    <input type="hidden" value="1"
                           name="page"/>
                    <input type="checkbox" name="age18" value="v">
                    <label for="age18">Remember for 24h.</label>

                    <select id="adult" name="adult" required>
                        <option value="true" selected>Yes, I am over 18.</option>
                        <option value="false">No, I am not.</option>
                    </select>
                    <i>  </i>


                    <button class="btn btn-secondary" type="submit">Confirm</button>


                </form>
            </div>
        </div>
    </div>
</div>