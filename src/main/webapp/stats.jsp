<h2>Statistiques</h2>
                    <div>
                        <fieldset>
                            <legend>Par mois</legend>
                        <%
                           SimpleDateFormat sdfM = new SimpleDateFormat("MMMM");
                           if(names.contains("mpm")){
                               ArrayList<String> mpm = (ArrayList<String>) request.getAttribute("mpm");
                               for (int i = 0; i < mpm.size(); i+=2) {
                                    StringTokenizer stk = new StringTokenizer(mpm.get(i).toString(), "-");
                                        int nb = stk.countTokens();
                                        int[] toks = new int[nb];

                                        for (int k = 0; k < nb; k++) {
                                            String kk = stk.nextToken().trim();
                                            toks[k] = Integer.valueOf(kk);
                                            if (k == 1) {
                                                toks[k]--;
                                            }
                                        }
                                        Date date2 = new Date(toks[0], toks[1], 01);
                            %>
                                    <h3><%=sdfM.format(date2).toString() +" "+ String.valueOf(toks[0]) %> : <span class="chiffre"><%= mpm.get(i+1).toString().concat(" &#8364;") %></span></h3>
                            <%
                           }
                       }
                        %>
                        </fieldset>
                        <fieldset>
                            <legend>Par description</legend>
                        <%
                           if(names.contains("mpd")){
                               ArrayList<String> mpd = (ArrayList<String>) request.getAttribute("mpd");
                               for (int i = 0; i < mpd.size(); i+=2) {
                            %>
                                    <h3><%=mpd.get(i).toString() %> : <span class="chiffre"><%= mpd.get(i+1).toString().concat(" &#8364;") %></span></h3>
                            <%
                           }
                       }
                        %>
                      </fieldset>
                      <fieldset>
                            <legend>Par mois et par description</legend>
                        <%
                           if(names.contains("mpmd")){
                               ArrayList<String[]> mpmd = (ArrayList<String[]>) request.getAttribute("mpmd");
                               for (int i = 0; i < mpmd.size(); i++) {
                                   String curent = mpmd.get(i)[0].toString();
                                   String prev = mpmd.get(i-1)[0].toString();
                                   StringTokenizer stk = new StringTokenizer(mpmd.get(i)[0].toString(), "-");
                                        int nb = stk.countTokens();
                                        int[] toks = new int[nb];

                                        for (int k = 0; k < nb; k++) {
                                            String kk = stk.nextToken().trim();
                                            toks[k] = Integer.valueOf(kk);
                                            if (k == 1) {
                                                toks[k]--;
                                            }
                                        }
                                    Date date2 = new Date(toks[0], toks[1], 01);


                                    if(i==0){
                            %>
                                    <h3><%=sdfM.format(date2).toString() +" "+ String.valueOf(toks[0]) %> </h3>
                                    <div><%=mpmd.get(i)[1].toString() %> : <span class="chiffre"><%= mpmd.get(i)[2].toString().concat(" &#8364;") %></span></div>
                            <%
                                    }else {
                                        if(curent.equals(prev)){
                            %>
                                    <div><%=mpmd.get(i)[1].toString() %> : <span class="chiffre"><%= mpmd.get(i)[2].toString().concat(" &#8364;") %></span></div>
                            <%
                                        }else {
                             %>
                                    <h3><%=sdfM.format(date2).toString() +" "+ String.valueOf(toks[0]) %> </h3>
                                    <div><%=mpmd.get(i)[1].toString() %> : <span class="chiffre"><%= mpmd.get(i)[2].toString().concat(" &#8364;") %></span></div>
                            <%
                                        }
                                    }
                               }
                           }
                        %>
                      </fieldset>
                    </div>