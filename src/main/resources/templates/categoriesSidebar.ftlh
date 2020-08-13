<div class="d-none d-md-block">
    <div class="sticky-scroll">
        <div class="list-group  list-element  sidebar-list p-0 " id="ID01">

            <#list categories as category>
                <a class="list-group-item sidebar-list "
                        <#if queryName!?contains((category.id)?string)>
                            style="background-color: #1b1e21"
                        </#if>
                >
                    ${category.name}
                    <p hidden> ${category.id} </p>

                </a>

            </#list>


        </div>

        <div class="list-group  list-element  sidebar-list p-0" id="ID02">
            <a class="list-group-item sidebar-list "
                    <#if queryName!?contains("Alcoholic") >
                        style="background-color: #1b1e21"
                    </#if>
            >
                Alcoholic
            </a>

            <a class="list-group-item sidebar-list "
                    <#if queryName!?contains("Non alcoholic")>
                        style="background-color: #1b1e21"
                    </#if>
            >
                Non alcoholic
            </a>

            <a class="list-group-item sidebar-list "
                    <#if queryName!?contains("Optional alcohol")>
                        style="background-color: #1b1e21"
                    </#if>
            >
                Optional alcohol
            </a>
        </div>

    </div>

</div>
