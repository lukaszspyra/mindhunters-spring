<div>
    <form method="get" action="/search">
        <div class="list-group  list-element">
            <div class="list-group-item sidebar-list">
                <input type="hidden" name="search" value="name"/>
                <input type="text" required maxlength="30" minlength="2" name="name" id="input-name"
                       placeholder="Name...">
            </div>
        </div>
        <br>
        <input id="search-by-name" class="rounded-input" type="submit" value="Search by name">
        <br><br><br>
    </form>

    <form method="get" action="/search">
        <div class="list-group  list-element add-group">

            <div class="list-group-item sidebar-list">
                <input type="hidden" name="search" value="ingr"/>
                <input type="text" required maxlength="20" minlength="2" name="ing"
                       placeholder="Ingredient..." class="input-ingredientName"/>
                <a href="javascript:void(0);" class="add_button " id="remove_blue" title="Add field">
                    <svg class="bi bi-plus-square-fill" id="remove_blue" width="1em" height="1em"
                         viewBox="0 0 16 16"
                         fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd"
                              d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm6.5 4a.5.5 0 0 0-1 0v3.5H4a.5.5 0 0 0 0 1h3.5V12a.5.5 0 0 0 1 0V8.5H12a.5.5 0 0 0 0-1H8.5V4z"/>
                    </svg>
                </a>
                <br>
            </div>

        </div>
        <br>
        <input type="submit" class="rounded-input"
               value="Search by ingredients"/>
        <br/><br><br>
    </form>
</div>